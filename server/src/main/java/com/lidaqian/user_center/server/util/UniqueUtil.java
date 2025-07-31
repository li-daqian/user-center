package com.lidaqian.user_center.server.util;

import java.util.concurrent.atomic.AtomicLong;

public class UniqueUtil {

    private static final SnowflakeIdGenerator ID_GENERATOR = new SnowflakeIdGenerator(1, 1);

    public static String shortId() {
        long id = ID_GENERATOR.nextId();
        return Base62.encode(id);
    }

    public static String simpleUuid() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

    private static class Base62 {
        private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        public static String encode(long value) {
            StringBuilder sb = new StringBuilder();
            do {
                int i = (int) (value % 62);
                sb.append(CHARACTERS.charAt(i));
                value /= 62;
            } while (value > 0);
            return sb.reverse().toString();
        }
    }

    private static class SnowflakeIdGenerator {
        private final long workerId;
        private final long datacenterId;
        private final AtomicLong sequence = new AtomicLong(0L);
        private final long twepoch = 1288834974657L;
        private final long workerIdBits = 5L;
        private final long datacenterIdBits = 5L;
        private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
        private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
        private final long sequenceBits = 12L;
        private final long workerIdShift = sequenceBits;
        private final long datacenterIdShift = sequenceBits + workerIdBits;
        private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
        private final long sequenceMask = -1L ^ (-1L << sequenceBits);
        private long lastTimestamp = -1L;

        public SnowflakeIdGenerator(long workerId, long datacenterId) {
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException("worker Id out of range");
            }
            if (datacenterId > maxDatacenterId || datacenterId < 0) {
                throw new IllegalArgumentException("datacenter Id out of range");
            }
            this.workerId = workerId;
            this.datacenterId = datacenterId;
        }

        public synchronized long nextId() {
            long timestamp = timeGen();
            if (timestamp < lastTimestamp) {
                throw new RuntimeException("Clock moved backwards.");
            }
            if (lastTimestamp == timestamp) {
                long seq = (sequence.incrementAndGet()) & sequenceMask;
                if (seq == 0) {
                    timestamp = tilNextMillis(lastTimestamp);
                }
            } else {
                sequence.set(0L);
            }
            lastTimestamp = timestamp;
            return ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence.get();
        }

        private long tilNextMillis(long lastTimestamp) {
            long timestamp = timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }

        private long timeGen() {
            return System.currentTimeMillis();
        }
    }
}
