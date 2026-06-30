#include <jni.h>
#include <string>
#include <cmath>
#include <cstdlib>
#include <ctime>
#include <android/log.h>

#define LOG_TAG "ECUSimulator"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

static float simSpeed     = 0.0f;
static float simRPM       = 800.0f;
static float simCoolant   = 90.0f;
static float simFuel      = 75.0f;
static float simThrottle  = 0.0f;
static float simLoad      = 0.0f;
static long  simOdometer  = 12450;
static float simTripDist  = 0.0f;
static long  simTripSecs  = 0;
static float simFuelUsed  = 0.0f;
static bool  simRunning   = false;
static int   simTick      = 0;

static float randomFloat(float min, float max) {
    return min + static_cast<float>(rand()) / RAND_MAX * (max - min);
}

extern "C" {

JNIEXPORT void JNICALL
Java_com_driveos_dashboard_service_CANBusService_initSimulator(
        JNIEnv* env, jobject /* this */) {
    srand(static_cast<unsigned>(time(nullptr)));
    simRunning = true;
    LOGI("ECU Simulator initialized");
}

JNIEXPORT jbyteArray JNICALL
Java_com_driveos_dashboard_service_CANBusService_getNextSimulatedFrame(
        JNIEnv* env, jobject /* this */) {

    simTick++;

    // Simulate drive cycle
    if (simTick % 100 < 60) {
        // Accelerating
        simThrottle = randomFloat(20.0f, 60.0f);
        simSpeed    = fminf(simSpeed + randomFloat(0.5f, 2.0f), 120.0f);
        simRPM      = 800.0f + simSpeed * 25.0f + randomFloat(-100, 100);
        simLoad     = simThrottle * 0.8f;
    } else {
        // Decelerating
        simThrottle = randomFloat(0.0f, 5.0f);
        simSpeed    = fmaxf(simSpeed - randomFloat(0.5f, 3.0f), 0.0f);
        simRPM      = fmaxf(800.0f, simRPM - randomFloat(50, 200));
        simLoad     = 5.0f;
    }

    // Warm up coolant
    if (simCoolant < 95.0f) simCoolant += 0.05f;
    simCoolant += randomFloat(-0.1f, 0.1f);

    // Fuel consumption
    float fuelRate = (simLoad / 100.0f) * 0.002f;
    simFuel      = fmaxf(0.0f, simFuel - fuelRate);
    simFuelUsed += fuelRate;

    // Trip stats
    simTripDist += simSpeed / 3600.0f;
    simTripSecs++;
    simOdometer = 12450 + static_cast<long>(simTripDist);

    // Pick which PID to report this tick
    int pid = simTick % 8;
    jbyteArray frame = env->NewByteArray(10);
    jbyte buf[10] = {0};

    // Byte 0: response mode (0x41 = OBD response)
    buf[0] = 0x04;   // length
    buf[1] = 0x41;   // mode response

    switch (pid) {
        case 0: { // RPM
            buf[2] = 0x0C;
            int r = static_cast<int>(simRPM * 4);
            buf[3] = (r >> 8) & 0xFF;
            buf[4] = r & 0xFF;
            break;
        }
        case 1: { // Speed
            buf[2] = 0x0D;
            buf[3] = static_cast<jbyte>(simSpeed);
            break;
        }
        case 2: { // Coolant
            buf[2] = 0x05;
            buf[3] = static_cast<jbyte>(simCoolant + 40);
            break;
        }
        case 3: { // Throttle
            buf[2] = 0x11;
            buf[3] = static_cast<jbyte>((simThrottle / 100.0f) * 255);
            break;
        }
        case 4: { // Fuel level
            buf[2] = 0x2F;
            buf[3] = static_cast<jbyte>((simFuel / 100.0f) * 255);
            break;
        }
        case 5: { // Engine load
            buf[2] = 0x04;
            buf[3] = static_cast<jbyte>((simLoad / 100.0f) * 255);
            break;
        }
        case 6: { // Trip distance (custom 0xA1)
            buf[2] = 0xA1;
            int d = static_cast<int>(simTripDist * 10);
            buf[3] = (d >> 8) & 0xFF;
            buf[4] = d & 0xFF;
            break;
        }
        case 7: { // Fuel consumed (custom 0xA3)
            buf[2] = 0xA3;
            int f = static_cast<int>(simFuelUsed * 100);
            buf[3] = (f >> 8) & 0xFF;
            buf[4] = f & 0xFF;
            break;
        }
    }

    env->SetByteArrayRegion(frame, 0, 10, buf);
    return frame;
}

JNIEXPORT void JNICALL
Java_com_driveos_dashboard_service_CANBusService_stopSimulator(
        JNIEnv* env, jobject /* this */) {
    simRunning = false;
    LOGI("ECU Simulator stopped");
}

} // extern "C"