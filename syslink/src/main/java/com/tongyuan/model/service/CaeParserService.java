package com.tongyuan.model.service;

import com.tongyuan.exception.ParseCaeException;
import org.dom4j.Element;

public interface CaeParserService {
    void parseCAE(Element root) throws ParseCaeException;
}
