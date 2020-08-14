g++ fib1.cpp -std=c++11 -DMULTICORE -pthread -O3 -o fib-parallel.exe
g++ fib1.cpp -std=c++11 -O3 -o fib-serial.exe

# icpc fib.cpp -std=c++11 -DMULTICORE -pthread -O3 -o fib-parallel.exe
# icpc fib.cpp -std=c++11 -O3 -o fib-serial.exe

echo " PARALLEL "
time ./fib-parallel.exe

echo " SERIAL "
time ./fib-serial.exe

  
