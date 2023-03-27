#include <chrono>
#include <cstdint>
#include <iostream>
#include <chrono>  // chrono::system_clock
#include <ctime>   // localtime
#include <iomanip> // put_time

using namespace std;
using namespace std::chrono;

uint64_t timeSinceEpochMillisec() {

  return duration_cast<milliseconds>(system_clock::now().time_since_epoch()).count();
}

int main() {
  cout << timeSinceEpochMillisec() << endl;
  auto now = chrono::system_clock::now();
  auto in_time_t = chrono::system_clock::to_time_t(now);
  cout << put_time(localtime(&in_time_t), "%Y-%m-%d %I:%M:%S %p") << endl;
  return 0;
}

