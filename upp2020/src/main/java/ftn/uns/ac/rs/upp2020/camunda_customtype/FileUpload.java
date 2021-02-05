package ftn.uns.ac.rs.upp2020.camunda_customtype;

import org.camunda.bpm.engine.impl.form.type.SimpleFormFieldType;
import org.camunda.bpm.engine.variable.value.TypedValue;

public class FileUpload extends SimpleFormFieldType {

    public String TYPE_NAME = "fileUpload";

    @Override
    protected TypedValue convertValue(TypedValue propertyValue) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        return TYPE_NAME;
    }

    @Override
    public Object convertFormValueToModelValue(Object propertyValue) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String convertModelValueToFormValue(Object modelValue) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
