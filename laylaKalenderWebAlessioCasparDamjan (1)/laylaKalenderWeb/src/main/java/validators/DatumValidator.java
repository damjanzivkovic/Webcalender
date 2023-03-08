package validators;

import controller.KalenderBean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@FacesValidator("datumInZukunft")
public class DatumValidator implements Validator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Date now = new Date();
        Date inputDate = (Date) value;

        if(inputDate.before(now)){
            FacesMessage msg = new FacesMessage("Datum liegt in der Vergangenheit.",
                    "Datum kann nicht in der Vergangenheit liegen.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
