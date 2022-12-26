// Vector Dot Product

#include <stdlib.h>

void vector_add(int n, double *array_in1, double *array_in2, double *array_out)
{
    for (int i = 0; i < n; i++)
        array_out[i] = array_in1[i] + array_in2[i];
}

void vector_sub(int n, double *array_in1, double *array_in2, double *array_out)
{
    for (int i = 0; i < n; i++)
        array_out[i] = array_in1[i] - array_in2[i];
}

void vector_dot(int n, double *array_in1, double *array_in2, double* product)
{
    product[0] = 0;
    for (int i = 0; i < n; i++)
        product[0] += array_in1[i] * array_in2[i];
}
