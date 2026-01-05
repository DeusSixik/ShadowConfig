package net.shadowking21.shadowconfig.config.models;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class RandomConfig
{
    @JsonPropertyDescription("Just a random commentary for random value")
    public String randomValue = "random";

    @JsonPropertyDescription("Just a random commentary for random value TWO")
    public String randomValueTwo = "randomTwo";

    public RandomConfig()
    {

    }

}