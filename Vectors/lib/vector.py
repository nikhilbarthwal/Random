from ctypes import c_double, c_int, CDLL
from typing import List

lib_path = 'bin/vector.so'

try:
    basic_function_lib = CDLL(lib_path)
except:
    raise Exception('Could not find shared object')

vector_add = basic_function_lib.vector_add
vector_sub = basic_function_lib.vector_sub
vector_dot = basic_function_lib.vector_dot

vector_add.restype = None
vector_sub.restype = None
vector_dot.restype = None


def add(in1: List[float], in2: List[float]) -> List[float]:
    assert len(in1) == len(in2)
    n = len(in1)
    c_arr_in1 = (c_double * n)(*in1)
    c_arr_in2 = (c_double * n)(*in2)
    c_arr_out = (c_double * n)()

    vector_add(c_int(n), c_arr_in1, c_arr_in2, c_arr_out)
    return c_arr_out[:]  #  truncate(c_arr_out[:])


def sub(in1: List[float], in2: List[float]) -> List[float]:
    assert len(in1) == len(in2)
    n = len(in1)
    c_arr_in1 = (c_double * n)(*in1)
    c_arr_in2 = (c_double * n)(*in2)
    c_arr_out = (c_double * n)()

    vector_sub(c_int(n), c_arr_in1, c_arr_in2, c_arr_out)
    return c_arr_out[:]

def dot(in1: List[float], in2: List[float]) -> float:
    assert len(in1) == len(in2)
    n = len(in1)
    c_arr_in1 = (c_double * n)(*in1)
    c_arr_in2 = (c_double * n)(*in2)
    c_arr_out = (c_double* 1)()
    vector_dot(c_int(n), c_arr_in1, c_arr_in2, c_arr_out)
    return c_arr_out[0]
