LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := renderscript
LOCAL_C_INCLUDES += $(TARGET_C_INCLUDES)/rs/cpp \
                    $(TARGET_C_INCLUDES)/rs \
                    $(TARGET_OBJS)/$(LOCAL_MODULE)
LOCAL_SRC_FILES := RenderScript.cpp threshold.rs
LOCAL_LDFLAGS += -L$(call host-path,$(TARGET_C_INCLUDES)/../lib/rs)
LOCAL_LDLIBS    := -ljnigraphics -ldl -llog -lRScpp_static

include $(BUILD_SHARED_LIBRARY)
