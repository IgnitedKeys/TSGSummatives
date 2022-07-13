/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.controller;

import com.sg.dvdlibrary.dao.DVDLibraryDao;
import com.sg.dvdlibrary.dao.DVDLibraryDaoException;
import com.sg.dvdlibrary.dto.DVD;
import com.sg.dvdlibrary.ui.DVDLibraryView;
import java.util.List;

/**
 *
 * @author angeladrees
 */
public class DVDLibraryController {

    //remove later!
    private DVDLibraryView view;
    private DVDLibraryDao dao;

    public DVDLibraryController(DVDLibraryDao dao, DVDLibraryView view) {
        this.dao = dao;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        listDVDs();
                        break;
                    case 2:
                        createDVD();
                        break;
                    case 3:
                        deleteDVD();
                        break;
                    case 4:
                        editDVDInfo();
                        break;
                    case 5:
                        getDVDInfo();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                        break;
                }
            }
            goodbye();

        } catch (DVDLibraryDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

    private int getMenuSelection() {
        return view.printMenuAndSelection();
    }

    private void createDVD() throws DVDLibraryDaoException {
        view.displayAddDvdBanner();
        DVD newDvd = view.getNewDvdInfo();
        dao.addDVD(newDvd.getTitle(), newDvd);
        view.displayAddDvdSuccess();
    }

    private void listDVDs() throws DVDLibraryDaoException {
        view.displayAllDvdBanner();
        List<DVD> dvdList = dao.getAllDVDs();
        view.displayDvdList(dvdList);
        view.displaySuccessBanner();
    }

    private void deleteDVD() throws DVDLibraryDaoException {
        view.displayDeleteDvdBanner();
        String title = view.getDVDTitle();
        DVD removedDVD = dao.removeDVD(title);
        view.displayRemoveDVD(removedDVD);
    }

    private void getDVDInfo() throws DVDLibraryDaoException {
        view.displayDVDInfoBanner();
        String dvdTitle = view.getDVDTitle();
        DVD dvd = dao.getDVD(dvdTitle);
        view.displayDVDInfo(dvd);
    }

    private void editDVDInfo() throws DVDLibraryDaoException {
        view.displayEditDVDBanner();
        String dvdTitle = view.getDVDTitle();
        DVD dvd = dao.getDVD(dvdTitle);
        boolean exist = view.dvdExist(dvd);
        if (exist) {
            view.editInfoMenu(dvdTitle, dvd);
            dao.editDVD(dvdTitle, dvd);
        } else {
            view.dvdNotExist();
        }
        view.displaySuccessBanner();

    }

    private void goodbye() {
        view.displayGoodBye();
    }

    private void unknownCommand() {
        view.unknownCommand();
    }
}
