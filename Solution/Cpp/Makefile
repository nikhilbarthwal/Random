CC = g++
CFLAGS = -Wunused-variable
SRCS = src/Main.cpp src/Url.cpp src/Time.cpp src/Utils.cpp
HEADER = src/Common.h src/Config.h
OBJS = $(SRCS:.cpp=.o)
MAIN = solution

$(MAIN): $(OBJS) $(HEADER)
	$(CC) $(CFLAGS) -o $(MAIN) $(OBJS)

.cpp.o: $(HEADER)
	$(CC) -c $< -o $@

clean:
	rm $(MAIN) $(OBJS)