cmake_minimum_required(VERSION 3.28)
project(Library)

set(CMAKE_CXX_STANDARD 14)

add_executable(Library main.cpp
        vector.h
        set.h
        string.h
        map.h
        pair.h)
