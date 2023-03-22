    cmake -B ./build/ -G "Ninja Multi-Config" -DCMAKE_C_COMPILER=clang
    cmake --build ./build/ --config ASan  --target check
    cmake --build ./build/ --config LSan  --target check
    cmake --build ./build/ --config MSan  --target check
    cmake --build ./build/ --config UBSan --target check