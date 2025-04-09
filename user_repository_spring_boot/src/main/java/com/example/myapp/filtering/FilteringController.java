package com.example.myapp.filtering;

import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FilteringController {

//    field1, field2
    @GetMapping(path = "/filtering")
    public MappingJacksonValue retrieveSomeBean(){
        SomeBean someBean = new SomeBean("Value1", "Value2", "Value3");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");

        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }
//    field2, field3
    @GetMapping(path = "/filteringList")
    public MappingJacksonValue retrieveListSomeBean(){
        List<SomeBean> list = new ArrayList<SomeBean>();
        list.add(new SomeBean("Value1","Value2","Value3"));
        list.add(new SomeBean("V1","V2","V3"));

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field3", "field2");

        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;

    }
}
