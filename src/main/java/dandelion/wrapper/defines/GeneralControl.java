package dandelion.wrapper.defines;

import dandelion.wrapper.returns.GeneralReturns;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.annotation.Resource;
import java.util.Locale;

/** @author Marcus */
@Slf4j
public abstract class GeneralControl implements GeneralReturns {

  @Resource private MessageSource messageSource;
  private final ConfigurableApplicationContext configurable;

  private final Locale locale = LocaleContextHolder.getLocale();

  public GeneralControl(ConfigurableApplicationContext configurable) {
    this.configurable = configurable;
  }

  protected final String property(final String prop) {
    return configurable.getEnvironment().getProperty(prop);
  }

  protected final String convertMessage(String message) {
    return convertMessage(message, locale, false);
  }

  protected final String convertMessage(String message, Locale locale, boolean convert) {
    String result;
    if (convert) {
      try {
        result = messageSource.getMessage(message, null, locale);
      } catch (NoSuchMessageException e) {
        log.warn("NoSuchMessage : [ {} ]", message);
        result = message;
      }
    } else {
      result = message;
    }
    return result;
  }
}
