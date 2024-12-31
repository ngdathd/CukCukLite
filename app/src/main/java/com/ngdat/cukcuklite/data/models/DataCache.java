package com.ngdat.cukcuklite.data.models;

import com.ngdat.cukcuklite.data.cukcukenum.DataCacheEnum;

/**
 * Lớp thông tin chứa dữ liệu chờ cập nhật lên server
 * Created at 24/04/2019
 */
public class DataCache {
    private String Id;
    private DataCacheEnum Type;
    private String Data;

    public DataCache(Builder builder) {
        Id = builder.Id;
        Type = builder.Type;
        Data = builder.Data;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public DataCacheEnum getType() {
        return Type;
    }

    public void setType(DataCacheEnum type) {
        Type = type;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public static class Builder {
        private String Id;
        private DataCacheEnum Type;
        private String Data;

        public Builder setId(String id) {
            Id = id;
            return this;
        }

        public Builder setType(DataCacheEnum type) {
            Type = type;
            return this;
        }

        public Builder setData(String data) {
            Data = data;
            return this;
        }

        public DataCache build() {
            return new DataCache(this);
        }
    }
}
