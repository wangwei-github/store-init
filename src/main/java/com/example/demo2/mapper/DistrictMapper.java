package com.example.demo2.mapper;

import com.example.demo2.entity.District;

import java.util.List;


public interface DistrictMapper {

    List<District> getByParent(String parent);
}
