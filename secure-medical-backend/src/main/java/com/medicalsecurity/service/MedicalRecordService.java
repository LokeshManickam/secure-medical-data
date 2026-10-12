package com.medicalsecurity.service;

import com.medicalsecurity.entity.MedicalRecord;
import com.medicalsecurity.entity.Patient;
import com.medicalsecurity.repository.MedicalRecordRepository;
import com.medicalsecurity.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;

    public MedicalRecordService(
            MedicalRecordRepository medicalRecordRepository,
            PatientRepository patientRepository) {

        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * Creates a medical record for a patient.
     */
    public MedicalRecord createMedicalRecord(
            Long patientId,
            MedicalRecord record) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Patient not found"
                        ));

        record.setPatient(patient);

        return medicalRecordRepository.save(record);
    }

    /**
     * Returns all medical records belonging to a patient.
     */
    public List<MedicalRecord> getPatientMedicalRecords(
            Long patientId) {

        if (!patientRepository.existsById(patientId)) {
            throw new IllegalArgumentException(
                    "Patient not found"
            );
        }

        return medicalRecordRepository
                .findByPatient_IdOrderByRecordDateDesc(patientId);
    }

    /**
     * Returns one medical record by ID.
     */
    public MedicalRecord getMedicalRecordById(
            Long id) {

        return medicalRecordRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Medical record not found"
                        ));
    }

    /**
     * Updates an existing medical record.
     */
    public MedicalRecord updateMedicalRecord(
            Long id,
            MedicalRecord updatedRecord) {

        MedicalRecord existingRecord =
                getMedicalRecordById(id);

        existingRecord.setRecordDate(
                updatedRecord.getRecordDate()
        );

        existingRecord.setRecordType(
                updatedRecord.getRecordType()
        );

        existingRecord.setNotes(
                updatedRecord.getNotes()
        );

        existingRecord.setTreatment(
                updatedRecord.getTreatment()
        );

        return medicalRecordRepository.save(existingRecord);
    }
}