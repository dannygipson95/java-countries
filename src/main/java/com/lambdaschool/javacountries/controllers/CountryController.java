package com.lambdaschool.javacountries.controllers;

import com.lambdaschool.javacountries.models.Country;
import com.lambdaschool.javacountries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {
    @Autowired
    CountryRepository countryrepos;

    private List<Country> findCountries(List<Country> countryList, CountryChecker tester){
        List<Country> tempList = new ArrayList<>();

        for (Country c: countryList){
            if (tester.test(c)){
                tempList.add(c);
            }
        }
        return tempList;
    }

    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries(){
        List<Country> countryList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(countryList::add);
        countryList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }

    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> listCountryByLetter(@PathVariable char letter){
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        List<Country> letterList = findCountries(myList, c -> c.getName().charAt(0) == letter);
        letterList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(letterList, HttpStatus.OK);
    }

    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> sumPopulation(){
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        long totalPop = 0;

        for (Country c : myList){
            totalPop += c.getPopulation();
        }

        System.out.println("Total population: " + totalPop);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/population/min", produces = {"application/json"})
    public ResponseEntity<?> findMinPopulation(){
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> Long.compare(c1.getPopulation(), c2.getPopulation()));
        return new ResponseEntity<>(myList.get(0), HttpStatus.OK);

    }

    @GetMapping(value = "/population/max", produces = {"application/json"})
    public ResponseEntity<?> findMaxPopulation(){
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> Long.compare(c2.getPopulation(), c1.getPopulation()));
        return new ResponseEntity<>(myList.get(0), HttpStatus.OK);

    }

    @GetMapping(value = "/population/median", produces = {"application/json"})
    public ResponseEntity<?> findMedianPopulation(){
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList:: add);
        int median = (1 + myList.size())/2;
        return new ResponseEntity<>(myList.get(median -1), HttpStatus.OK);
    }
}