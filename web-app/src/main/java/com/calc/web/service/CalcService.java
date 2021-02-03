package com.calc.web.service;

import com.calc.pub.BaseService;
import com.calc.web.model.Calc;

import java.util.List;

public interface CalcService extends BaseService<Calc> {

    List<Calc> getNext(int size, String tag);

    List<Calc> generator(int size, String tag);

    void compute(Calc entity);

    void compute2(Calc entity);

    int getCountIsExist(Calc calc);

}