package com.cognizant.EMS.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.EMS.Exception.ResourceNotFoundException;
import com.cognizant.EMS.entity.TrainingSlot;
import com.cognizant.EMS.service.TrainingSlotService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/training-slot")
@Slf4j
public class TrainingSlotController {
	
	@Autowired
	private TrainingSlotService trainingslotService;
	

	public TrainingSlotController(TrainingSlotService trainingslotService) {
		this.trainingslotService = trainingslotService;
	}
	
	@GetMapping
	public List<TrainingSlot> getAllTrainingSlot() {
		log.info("Successfully fetched all the  employee's Training Slot detail's ");
		return trainingslotService.getAllTrainingSlot();
	}
	
	@GetMapping("/{id}")
	public Optional<TrainingSlot> getTrainingSlotById(@PathVariable Long id) throws ResourceNotFoundException {
		log.info("Successfully fetched the employee Training Slot detail's ");	
		 Optional<TrainingSlot> trainingslot = trainingslotService.getTrainingSlotById(id);
		    
		    if (!trainingslot.isPresent()) {
		        throw new ResourceNotFoundException("Not Found ");
		    } 
		return trainingslotService.getTrainingSlotById(id);
	}
	
	@PostMapping
    public ResponseEntity<TrainingSlot> getEmployeeById(@RequestBody TrainingSlot trainingslot) throws ResourceNotFoundException{
		TrainingSlot createdtrainingslot = trainingslotService.createTrainingSlot(trainingslot);
        log.info("Successfully created");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdtrainingslot);
	}
    
	@PutMapping("/{id}")
	public ResponseEntity<TrainingSlot> updateTrainingSlot(@PathVariable Long id, @RequestBody TrainingSlot trainingslot) 
			throws ResourceNotFoundException {
		log.info("Successfully updated the employee Training Slot detail's ");
		Optional<TrainingSlot> TrainingSlot = trainingslotService.getTrainingSlotById(id);
		if (!TrainingSlot.isPresent()) {
			throw new ResourceNotFoundException("TrainingSlot not found with id: " + id);
			}
		trainingslot.setId(id);
		TrainingSlot updatedTrainingSlot = trainingslotService.updateTrainingSlot(id, trainingslot);
		return ResponseEntity.ok(updatedTrainingSlot);
		}
	 	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTrainingSlot(@PathVariable Long id) throws ResourceNotFoundException {
		log.info("Successfully deleted the employee Training Slot detail's ");
		Optional<TrainingSlot> trainingslot = trainingslotService.getTrainingSlotById(id);
		if (!trainingslot.isPresent()) {
			throw new ResourceNotFoundException("TrainingSlot not found with id: " + id);
			}
		trainingslotService.deleteTrainingSlot(id);
		return ResponseEntity.noContent().build();
		}

}
