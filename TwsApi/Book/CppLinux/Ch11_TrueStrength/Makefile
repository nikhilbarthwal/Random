include ../Makefile

PROJ=TrueStrength

CC=g++

CFLAGS=-std=c++11 -Wno-return-type

# Directory containing source files
SRC_DIR=$(TWS_DIR)/IBJts/source/cppclient/client

# Directory containing header files
INC_DIR=$(TWS_DIR)/IBJts/source/cppclient/client

$(PROJ): Main.cpp $(PROJ).cpp $(SRC_DIR)/*.cpp
	$(CC) $(CFLAGS) -o $@ $^ -I$(INC_DIR) -lpthread

.PHONY: clean

clean:
	rm $(PROJ)
