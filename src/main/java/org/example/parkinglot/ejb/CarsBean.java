package org.example.parkinglot.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.parkinglot.common.CarDto;
import org.example.parkinglot.entities.Car;
import org.example.parkinglot.entities.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;


@Stateless
public class CarsBean {
    private static final Logger LOGGER = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<CarDto> findAllCars() {
        LOGGER.info("findAllCars");
        try {
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDtos(cars);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    private List<CarDto> copyCarsToDtos(List<Car> cars) {
        List<CarDto> list = new ArrayList<>();
        for (Car car : cars) {
            CarDto temp = new CarDto(car.getId(),
                    car.getLicensePlate(),
                    car.getParkingSpot(),
                    car.getOwner().getUsername());
            list.add(temp);
        }
        return list;
    }

    public void createCar(String licensePlate, String parkingSpot, Long userId) {
        LOGGER.info("createCar");

        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        User user = entityManager.find(User.class, userId);
        user.getCars().add(car);
        car.setOwner(user);

        entityManager.persist(car);
    }

    public CarDto findById(Long id){

        Car car = entityManager.find(Car.class, id);

        return new CarDto(id, car.getLicensePlate(), car.getParkingSpot(), car.getOwner().getUsername());
    }

    public void updatedCar( Long cardId ,String licensePlate,String parkingSpot,Long userId){
        LOGGER.info("updateCar");

        Car car = entityManager.find(Car.class, cardId);
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        User oldUser=car.getOwner();
        oldUser.getCars().remove(car);


        User user = entityManager.find(User.class,userId);
        user.getCars().add(car);
        car.setOwner(user);
    }

    public void deleteCarsByIds(Collection<Long> carIds) {
        LOGGER.info("deleteCarsByIds");

        for(Long carId : carIds) {
            Car car = entityManager.find(Car.class, carId);
            entityManager.remove(car);
        }
    }

}