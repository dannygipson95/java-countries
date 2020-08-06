package com.lambdaschool.javacountries.controllers;

import com.lambdaschool.javacountries.models.Country;

public interface CountryChecker {
    boolean test(Country e);
}