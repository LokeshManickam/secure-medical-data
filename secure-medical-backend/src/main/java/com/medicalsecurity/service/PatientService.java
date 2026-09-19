package com.medicalsecurity.service;

import com.medicalsecurity.entity.Patient;
import com.medicalsecurity.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient createPatient(Patient patient) {

        if (patientRepository.existsByPatientCode(
                patient.getPatientCode())) {

            throw new IllegalArgumentException(
                    "Patient code already exists"
            );
        }

        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {

        return patientRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Patient not found"
                        ));
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {

        Patient existingPatient = getPatientById(id);

        existingPatient.setPatientCode(
                updatedPatient.getPatientCode()
        );
        existingPatient.setName(
                updatedPatient.getName()
        );
        existingPatient.setAge(
                updatedPatient.getAge()
        );
        existingPatient.setGender(
                updatedPatient.getGender()
        );
        existingPatient.setDiagnosis(
                updatedPatient.getDiagnosis()
        );

        return patientRepository.save(existingPatient);
    }

    public void deletePatient(Long id) {

        Patient patient = getPatientById(id);

        patientRepository.delete(patient);
    }
}