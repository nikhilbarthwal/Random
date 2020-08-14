g++ fib2.cpp -std=c++11 -DMULTICORE -fopenmp -O3 -o fib2-parallel.exe
g++ fib2.cpp -std=c++11 -O3 -o fib2-serial.exe

echo " PARALLEL "
time ./fib2-parallel.exe

echo " SERIAL "
time ./fib2-serial.exe

  
