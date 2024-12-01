package com.blackfox.estate.service;

import com.blackfox.estate.entity.Booking;
import com.blackfox.estate.exception.ResourceNotFoundException;
import com.blackfox.estate.repository.BookingRepository;
import com.blackfox.estate.repository.CustomerRepository;
import com.blackfox.estate.repository.HotelRoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final HotelRoomRepository hotelRoomRepository;

    public BookingService(BookingRepository bookingRepository,
                          CustomerRepository customerRepository,
                          HotelRoomRepository hotelRoomRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.hotelRoomRepository = hotelRoomRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));
    }

    public Booking createBooking(Booking booking) {
        // Перевірка, чи існують клієнт і номер
        customerRepository.findById(booking.getCustomer().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + booking.getCustomer().getId()));

        hotelRoomRepository.findById(booking.getHotelRoom().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel Room not found with ID: " + booking.getHotelRoom().getId()));

        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long id, Booking updatedBooking) {
        Booking existingBooking = getBookingById(id);
        existingBooking.setCheckInDate(updatedBooking.getCheckInDate());
        existingBooking.setCheckOutDate(updatedBooking.getCheckOutDate());
        existingBooking.setCustomer(updatedBooking.getCustomer());
        existingBooking.setHotelRoom(updatedBooking.getHotelRoom());

        return bookingRepository.save(existingBooking);
    }

    public void deleteBooking(Long id) {
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
    }
}
