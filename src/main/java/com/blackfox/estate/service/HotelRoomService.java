package com.blackfox.estate.service;

import com.blackfox.estate.entity.HotelRoom;
import com.blackfox.estate.exception.ResourceNotFoundException;
import com.blackfox.estate.repository.HotelRoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class HotelRoomService {

    private final HotelRoomRepository hotelRoomRepository;

    public HotelRoomService(HotelRoomRepository hotelRoomRepository) {
        this.hotelRoomRepository = hotelRoomRepository;
    }

    public List<HotelRoom> getAllHotelRooms() {
        return hotelRoomRepository.findAll();
    }

    public HotelRoom getHotelRoomById(Long id) {
        return hotelRoomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel Room not found with ID: " + id));
    }

    public HotelRoom createHotelRoom(HotelRoom hotelRoom) {
        return hotelRoomRepository.save(hotelRoom);
    }

    public HotelRoom updateHotelRoom(Long id, HotelRoom updatedHotelRoom) {
        HotelRoom existingRoom = getHotelRoomById(id);
        existingRoom.setRoomNumber(updatedHotelRoom.getRoomNumber());
        existingRoom.setRoomType(updatedHotelRoom.getRoomType());
        existingRoom.setCapacity(updatedHotelRoom.getCapacity());
        existingRoom.setPrice(updatedHotelRoom.getPrice());

        return hotelRoomRepository.save(existingRoom);
    }

    public void deleteHotelRoom(Long id) {
        HotelRoom hotelRoom = getHotelRoomById(id);
        hotelRoomRepository.delete(hotelRoom);
    }
}