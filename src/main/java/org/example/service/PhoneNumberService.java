package org.example.service;


import org.example.exeption.NotFoundException;
import org.example.servlet.dto.PhoneNumberIncomingDto;
import org.example.servlet.dto.PhoneNumberOutGoingDto;
import org.example.servlet.dto.PhoneNumberUpdateDto;

import java.util.List;

public interface PhoneNumberService {
    PhoneNumberOutGoingDto save(PhoneNumberIncomingDto phoneNumber);

    void update(PhoneNumberUpdateDto phoneNumber) throws NotFoundException;

    PhoneNumberOutGoingDto findById(Long phoneNumberId) throws NotFoundException;

    List<PhoneNumberOutGoingDto> findAll();

    boolean delete(Long phoneNumberId);
}
