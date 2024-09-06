SRC_DIR := src
OUT_DIR := bin

LWJGL_PATH ?= $(HOME)/javalibs/lwjgl
LWJGL_LIBS ?= $(LWJGL_PATH)/lwjgl.jar:$(LWJGL_PATH)/lwjgl-natives-linux.jar:\
$(LWJGL_PATH)/lwjgl-opengl.jar:$(LWJGL_PATH)/lwjgl-opengl-natives-linux.jar:\
$(LWJGL_PATH)/lwjgl-glfw.jar:$(LWJGL_PATH)/lwjgl-glfw-natives-linux.jar:\
$(LWJGL_PATH)/joml-1.10.7.jar:\
$(LWJGL_PATH)/lwjgl-stb.jar:$(LWJGL_PATH)/lwjgl-stb-natives-linux.jar #:\
#$(LWJGL_PATH)/lwjgl-openal.jar:$(LWJGL_PATH)/lwjgl-openal-natives-linux.jar

SRCS := $(wildcard $(SRC_DIR)/*/*/*/*.java) $(wildcard $(SRC_DIR)/*/*/*/*/*.java) $(wildcard $(SRC_DIR)/*/*/*/*/*/*.java)
#CLS := $(SRCS:$(SRC_DIR)/%.java=$(OUT_DIR)/%.class)

JC := javac
JCFLAGS := -d $(OUT_DIR)/ -cp $(SRC_DIR):$(LWJGL_LIBS) #-encoding iso-8859-1

.SUFFIXES: .java .class

.PHONY: all clean build run

all: build run

build: .done

run:
	java -cp $(LWJGL_LIBS):$(OUT_DIR) fr.svedel.game.Main

$(OUT_DIR)/%.class: $(SRC_DIR)/%.java
	$(JC) $(JCFLAGS) $<

.done: $(SRCS)
	$(JC) $(JCFLAGS) $?
	touch .done

clean:
	rm -rf $(OUT_DIR)
	rm -f .done
	rm -f *~ $(SRC_DIR)/*/*/*/*~ $(SRC_DIR)/*/*/*/*/*~ $(SRC_DIR)/*/*/*/*/*/*~ resources/shaders/*~
