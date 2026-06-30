cmake_minimum_required(VERSION 3.22.1)

project("driveos")

add_library(
    ecu_simulator
    SHARED
    ecu_simulator.cpp
)

find_library(
    log-lib
    log
)

target_link_libraries(
    ecu_simulator
    ${log-lib}
)