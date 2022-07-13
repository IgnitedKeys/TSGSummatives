/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;
import java.util.List;

/**
 *
 * @author angeladrees
 */
public interface DVDLibraryDao {
    
    /**
     * Adds a DVD to the Library.
     * @param title of DVD to add
     * @param dvd contains info on: release date,rating,director, studio and user rating.
     * @return DVD associated with title 
     */
    DVD addDVD(String title, DVD dvd) throws DVDLibraryDaoException;
    
    /**
     * Deletes the DVD from the Library.
     * @param title of DVD to remove.
     * @return DVD associated with title to be deleted from Library.
     */
    DVD removeDVD(String title) throws DVDLibraryDaoException;
    
    /**
     * Allows user to edit information from a DVD in library, from the given 
     * title.This includes editing: release date, MPAA rating, Director's name, Studio
     * ,User rating.
     * @param title of DVD to edit
     * @param dvd information of DVD from given title
     * @return DVD associated with title edited.
     */
    DVD editDVD(String title, DVD dvd) throws DVDLibraryDaoException;
    
    /**
     * Returns a list of all DVDs in Library.
     * 
     * @return List of all DVDs in Library. 
     */
    List<DVD> getAllDVDs() throws DVDLibraryDaoException;
    
    /**
     * Return DVD information from given title.
     * 
     * @param title of DVD to get information on
     * @return DVD associated with title, null if it does not exist.
     */
    DVD getDVD(String title) throws DVDLibraryDaoException;
    
}
