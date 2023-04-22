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
  
  cout<<"_______________________________________________"<<endl;
  std::time_t t = std::time(nullptr);
  std::cout << "UTC:   " << std::put_time(std::gmtime(&t), "%c %Z") << '\n'
            << "local: " << std::put_time(std::localtime(&t), "%c %Z") << '\n';
  cout<<"_______________________________________________"<<endl;

  using namespace std::chrono;
  auto timepoint = system_clock::now();
  auto coarse = system_clock::to_time_t(timepoint);
  auto fine = time_point_cast<std::chrono::milliseconds>(timepoint);

  char buffer[sizeof "9999-12-31 23:59:59.999"];
  std::snprintf(buffer + std::strftime(buffer, sizeof buffer - 3,
                                       "%F %T.", std::localtime(&coarse)),
                4, "%03lu", fine.time_since_epoch().count() % 1000);

  std::cout << buffer << '\n';
  return 0;
}

