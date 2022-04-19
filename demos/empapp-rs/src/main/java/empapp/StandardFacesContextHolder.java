package empapp;

import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
public class StandardFacesContextHolder implements FacesContextHolder {

    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
