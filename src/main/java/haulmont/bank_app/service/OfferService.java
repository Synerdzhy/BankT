package haulmont.bank_app.service;

import haulmont.bank_app.dao.OfferDAO;
import haulmont.bank_app.data.Offer;

import java.util.List;
import java.util.UUID;

public class OfferService {
    private final OfferDAO offerDAO;

    public OfferService() {
        this.offerDAO = new OfferDAO();
    }

    public Offer getID(UUID id) {
        return offerDAO.getById(id);
    }

    public List<Offer> getAllOffers() {
        return offerDAO.getAll();
    }

    public void save(Offer offer) {
        offerDAO.save(offer);
    }

    public boolean remove(Offer offer) {
        return offerDAO.remove(offer);
    }
}
