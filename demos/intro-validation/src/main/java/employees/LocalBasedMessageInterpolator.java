package employees;

import javax.validation.MessageInterpolator;
import java.util.Locale;

public class LocalBasedMessageInterpolator implements MessageInterpolator {

    private MessageInterpolator targetMessageInterpolator;

    private Locale locale;

    public LocalBasedMessageInterpolator(MessageInterpolator targetMessageInterpolator, Locale locale) {
        this.targetMessageInterpolator = targetMessageInterpolator;
        this.locale = locale;
    }

    @Override
    public String interpolate(String messageTemplate, Context context) {
        return targetMessageInterpolator.interpolate(messageTemplate, context,
                locale);
    }

    @Override
    public String interpolate(String messageTemplate, Context context, Locale locale) {
        return targetMessageInterpolator.interpolate(messageTemplate, context,
                locale);
    }
}
