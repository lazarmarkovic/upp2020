package ftn.uns.ac.rs.upp2020.camunda_customtype;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.form.type.SimpleFormFieldType;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectOneGenre extends SimpleFormFieldType {

    public String TYPE_NAME = "selectOneGenre";

    protected Map<String, String> values;

    public SelectOneGenre() {
        this.values = new HashMap<>();
    }

    public SelectOneGenre(Map<String, String> values) {
        this.values = values;
    }

    @Override
    protected TypedValue convertValue(TypedValue typedValue) {
        Object value = typedValue.getValue();
        if(value == null || String.class.isInstance(value)){
            return Variables.stringValue((String) value, typedValue.isTransient());
        }else {
            throw new ProcessEngineException("Value '"+value+"' is not of type strings.");

        }
    }


    @Override
    public String getName() {
        return TYPE_NAME;
    }

    @Override
    public Object convertFormValueToModelValue(Object o) {
        return o;
    }

    @Override
    public String convertModelValueToFormValue(Object o) {
        if (o != null){
            if (!(o instanceof String)){
                throw new ProcessEngineException("Model value should be a String");
            }
        }
        return (String)o;
    }

    public Map<String, String> getValues() {
        return values;
    }
}
