package com.grydtech.msstack.codegenerator.creator.templatehelpers;

import com.google.common.base.CaseFormat;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

public class ToKebabCase implements TemplateMethodModelEx {
    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list.size() != 1) {
            throw new TemplateModelException("Wrong arguments");
        }
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, String.valueOf(list.get(0)));
    }
}
