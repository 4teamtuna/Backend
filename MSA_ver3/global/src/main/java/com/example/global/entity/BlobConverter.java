package com.example.global.entity;
import jakarta.persistence.AttributeConverter;

public class BlobConverter implements AttributeConverter<byte[], byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(byte[] attribute) {
        return attribute;
    }

    @Override
    public byte[] convertToEntityAttribute(byte[] dbData) {
        return dbData;
    }
}