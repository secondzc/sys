package com.tongyuan.model.service.impl;

import com.tongyuan.exception.ParseCaeException;
import com.tongyuan.model.domain.CAE.CaeComponent;
import com.tongyuan.model.domain.CAE.CaeFile;
import com.tongyuan.model.domain.CAE.CaeVariable;
import com.tongyuan.model.service.CaeComponentService;
import com.tongyuan.model.service.CaeFileService;
import com.tongyuan.model.service.CaeParserService;
import com.tongyuan.model.service.CaeVariableService;
import com.tongyuan.tools.XmlHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaeParserServiceImpl implements CaeParserService{

    @Autowired
    private CaeComponentService caeComponentService;
    @Autowired
    private CaeFileService caeFileService;
    @Autowired
    private CaeVariableService caeVariableService;

    /**
     * 以下是CAE的解析过程
     */
    public void parseCAE(Element root) throws ParseCaeException {
        List<Element> generalInfoList = root.elements("GeneralInfo");
        if(generalInfoList.isEmpty()||generalInfoList.size()>1){
            throw new ParseCaeException();
        }
        Element generalInfo = generalInfoList.get(0);
        Element eleName = generalInfo.element("Name");
        Element eleAuthor = generalInfo.element("Author");
        Element eleDescription = generalInfo.element("Description");
        Element eleRequirements = generalInfo.element("Requirements");
        Element eleVersion = generalInfo.element("Version");
        Element eleRunOs = generalInfo.element("RunOS");
        Element eleIconFile = generalInfo.element("IconFile");
        if(eleName==null
                ||eleAuthor==null
                ||eleDescription==null
                ||eleIconFile==null
                ||eleRequirements==null
                ||eleVersion==null
                ||eleRunOs==null){
            throw new ParseCaeException();
        }
        CaeComponent caeComponent = new CaeComponent();
        caeComponent.setName(eleName.getTextTrim());
        caeComponent.setAuthor(eleAuthor.getTextTrim());
        caeComponent.setDescription(eleDescription.getTextTrim());
        caeComponent.setIconFile(eleIconFile.getTextTrim());
        caeComponent.setRunOS(eleRunOs.getTextTrim());
        caeComponent.setRequirements(eleRequirements.getTextTrim());
        //插入caeComponent，将得到的id传给File
        caeComponentService.add(caeComponent);
        Long caeComponentId = caeComponent.getId();
        parseFile(caeComponentId,root);
    }



    /**
     * 解析File
     */
    public  void parseFile(Long caeComponentId , Element root) throws ParseCaeException{
        //Files元素一定存在,Files下的File元素的个数可能为0，1，2
        List<Element> files = root.element("Files").elements("File");
        if(files.isEmpty()){
            return ;
        }
        if(files.size()>2){
            throw new ParseCaeException();
        }
        //File元素为1或2的情况
        for(int i=0;i<files.size();i++){
            Element file = files.get(i);
            //将file插入到数据库中得到id，利用这个id来提供给variables和caeComponent
            String templateFileName = file.attribute("TemplateFileName").getValue();
            String fileName = file.attribute("FileName").getValue();
            String delimitersType = file.attribute("DelimitersType").getValue();
            String format = file.attribute("Format").getValue();
            String type = file.attribute("Type").getValue();
            CaeFile caeFile = new CaeFile();
            caeFile.setDelimitersType(templateFileName);
            caeFile.setFileName(fileName);
            caeFile.setDelimitersType(delimitersType);
            caeFile.setFormat(format);
            caeFile.setType(type);
            caeFile.setCaeComponentId(caeComponentId);
            caeFileService.add(caeFile);
            Long caeFileId = caeFile.getId();
            parseVariable(caeFileId,file);
        }
    }

    /**
     * 解析variables
     * @param id  variable所在File的id
     * @param file  表示File的Element
     */
    public  void parseVariable(Long id,Element file){
        //File元素存在，则Variables元素一定存在
        List<Element> variables = file.element("Variables").elements("Variable");
        if(variables.isEmpty()){
            return ;
        }
        for(int i=0;i<variables.size();i++){
            Element variable = variables.get(i);
            CaeVariable caeVariable = new CaeVariable();
//            caeVariable.setName(variable.attribute("Name").getValue());
//            caeVariable.setCaeFileId(id);
//            caeVariable.setDelimiters(variable.attribute("Delimiters").getValue());
//            caeVariable.setDescription(variable.attribute("Description").getValue());
//            caeVariable.setEnumAliases(variable.attribute("EnumAliases").getValue());
//            caeVariable.setFormat(variable.attribute("Format").getValue());
//            caeVariable.setEnumValues(variable.attribute("EnumValues").getValue());
//            caeVariable.setFortranFormat(variable.attribute("FortranFormat").getValue());
//            caeVariable.setMax(variable.attribute("Max").getValue());
//            caeVariable.setMin(variable.attribute("Min").getValue());
//            caeVariable.setStartBookmark(variable.attribute("StartBookmark").getValue());
//            caeVariable.setType(variable.attribute("Type").getValue());
//            caeVariable.setUnits(variable.attribute("Units").getValue());
//            caeVariable.setValue(variable.attribute("Value").getValue());
//            caeVariable.setValueType(variable.attribute("ValueType").getValue());
            XmlHelper.xml2Object(caeVariable,variable);
            caeVariable.setCaeFileId(id);
            caeVariableService.add(caeVariable);
        }
    }
}
