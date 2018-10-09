package com.epam.jdi.light.ui.html;

/**
 * Created by Roman Iovlev on 14.02.2018
 * Email: roman.iovlev.jdi@gmail.com; Skype: roman.iovlev
 */

import com.epam.jdi.light.elements.base.BaseElement;
import com.epam.jdi.light.elements.base.UIElement;
import com.epam.jdi.light.settings.WebSettings;
import com.epam.jdi.light.ui.html.base.*;
import com.epam.jdi.light.ui.html.common.Button;
import com.epam.jdi.light.ui.html.common.TextArea;
import com.epam.jdi.light.ui.html.complex.Checklist;
import com.epam.jdi.light.ui.html.complex.RadioButtons;
import com.epam.jdi.light.ui.html.complex.RadioGroup;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.List;

import static com.epam.jdi.light.common.Exceptions.exception;
import static com.epam.jdi.light.common.UIUtils.GET_BUTTON;
import static com.epam.jdi.light.common.UIUtils.getButtonByName;
import static com.epam.jdi.light.elements.init.InitActions.INIT_RULES;
import static com.epam.jdi.light.settings.WebSettings.initialized;
import static com.epam.jdi.tools.ReflectionUtils.*;
import static com.epam.jdi.tools.pairs.Pair.$;

public class HtmlSettings {

    public static synchronized void init() {
        if (!initialized) {
            WebSettings.init();
            INIT_RULES.add(
                $(f -> isInterface(f, WebElement.class), info -> new HtmlElement()),
                $(f -> isInterface(f, Checklist.class), info -> new HtmlChecklist()),
                $(f -> isInterface(f, RadioGroup.class) || isInterface(f, RadioButtons.class),
                        info -> new HtmlRadioGroup()),
                $(f -> isInterface(f, BaseSelector.class), info -> new HtmlSelector()),
                $(f -> isInterface(f, TextArea.class), info -> new TextAreaElement()),
                $(f -> isInterface(f, BaseElement.class) , info -> new HtmlElement())
                );

            GET_BUTTON = (obj, buttonName) -> {
                List<Field> fields = getFields(obj, Button.class);
                if (fields.size() == 0)
                    fields = getFields(obj, WebElement.class);
                switch (fields.size()) {
                    case 0:
                        throw exception("Can't find any buttons on form '%s.", obj);
                    case 1:
                        return (UIElement) getValueField(fields.get(0), obj);
                    default:
                        return getButtonByName(fields, obj, buttonName);
                }
            };
        }
    }
}