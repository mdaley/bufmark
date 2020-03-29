package com.sequsoft.bufmark.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.Test;

public class CommonUtilsTest {

    @Test
    void test_compress_and_decompress() {
        String in = "this is a long string with some stuff in it.";

        byte[] compressed = CommonUtils.compress(in.getBytes());
        byte[] decompressed = CommonUtils.decompress(compressed);

        String out = new String(decompressed);

        assertEquals(in, out);

    }
}
