package com.epam.jdi.cucumber.stepdefs.en;

import com.epam.jdi.light.ui.html.common.Image;
import cucumber.api.java.en.Then;

import static com.epam.jdi.cucumber.Utils.getElementByName;
import static org.testng.Assert.assertEquals;

public class ImageStepsEN {

    @Then("^image \"([^\"]*)\" has source \"([^\"]*)\"$")
    public void imageSourceIs(String fieldName, String source) {
        Image img = getElementByName(fieldName);
        assertEquals(source, img.src());
    }

    @Then("^image \"([^\"]*)\" has alt \"([^\"]*)\"$")
    public void imageTooltipIs(String fieldName, String alt) {
        Image img = getElementByName(fieldName);
        assertEquals(alt, img.alt());
    }
}
