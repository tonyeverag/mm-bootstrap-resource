package com.everag.mobilemanifest.bootstrap.interfaces.web.rest;

import org.apache.catalina.connector.ClientAbortException;
import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.AssertTrue;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(1)
public class CommonErrorHandler {
    private final Logger log = LoggerFactory.getLogger(CommonErrorHandler.class);

    private final Map<String, String> uniqueKeyMessages = new HashMap<>();
    private final Map<String, String> foreignKeyMessages = new HashMap<>();
    private final Map<String, String> checkMessages = new HashMap<>();

    public static final String LOG_REQUEST_BODY_ATTRIBUTE_NAME = "key.to.logRequestBody";

    public CommonErrorHandler() {
        super();
        // Create some error cross-reference maps
        // Hopefully, we can figure out a way to infer some of these...
        uniqueKeyMessages.put("UQ_BTU_BTUNUMBER", "BTU Number must be unique");
        uniqueKeyMessages.put("UQ_CODE", "This Barcode has already been registered");
        uniqueKeyMessages.put("UQ_COMPANY_NAME", "Name must be unique");
        uniqueKeyMessages.put("UQ_SCHEDULER_NAME", "Scheduler name must be unique");
        uniqueKeyMessages.put("UQ_DIVISION_NAME", "Division name must be unique within a supplier");
        uniqueKeyMessages.put("UQ_DROPYARD_NAME", "Drop yard name must be unique within a company");
        uniqueKeyMessages.put("UQ_HAULER_COMPANY", "The company is already a hauler");
        uniqueKeyMessages.put("UQ_INTAPP_LOADIDENTIFIER", "Load Identifier must be unique within a Planning Application");
        uniqueKeyMessages.put("UQ_LOAD_MANIFEST", "The same manifest number cannot be on a load twice");
        uniqueKeyMessages.put("UQ_LOAD_TANKER", "Tanker already exists on this load");
        uniqueKeyMessages.put("UQ_LOAD_TANKER_POS", "Multiple tankers in the same position");
        uniqueKeyMessages.put("UQ_OWNER_TANKNUM", "Tank Number must be unique within a company");
        uniqueKeyMessages.put("UQ_OWNER_TANKID", "Tanker ID must be unique within a company");
        uniqueKeyMessages.put("UQ_OWNER_TRUCKID", "Truck Number must be unique within a company");
        uniqueKeyMessages.put("UQ_PLANNING_APP_LOAD", "Load cannot have more than one planned load per planning service");
        uniqueKeyMessages.put("UQ_PLANT_NAME", "Plant name must be unique within a company");
        uniqueKeyMessages.put("UQ_PLANT_TRANSPORT_LEG", "Load cannot be refused from the same plant more than once");
        uniqueKeyMessages.put("UQ_SAMPLEPURPOSE_DESC", "Description must be unique");
        uniqueKeyMessages.put("UQ_SEAL_LOCATION_DESCRIPTION", "Description must be unique");
        uniqueKeyMessages.put("UQ_SUPPLIER_COMPANY", "The company is already a supplier");
        uniqueKeyMessages.put("UQ_SUPPLYCOMPANY_PRODUCERID", "Producer Code must be unique within a supplier");
        uniqueKeyMessages.put("UQ_SYSUSER", "Username already in use");
        uniqueKeyMessages.put("UQ_UICONFIGURATION", "Version must be unique");
        uniqueKeyMessages.put("UQ_UI_CONFIGURATION_FIELD", "This Step Field already exists for the UI Configuration");
        uniqueKeyMessages.put("UQ_UI_VALIDATION", "Sequence must not be repeated within a UI Configuration");
        uniqueKeyMessages.put("UQ_UI_VALIDATION_PARAMETER", "Name must be unique within a UI Validation Type");
        uniqueKeyMessages.put("UQ_UI_VALIDATION_TYPE", "Name must be unique");

        // Master data FKs
        foreignKeyMessages.put("FK_CALIBRATION_TANK", "Unable to delete tank with calibration entries");
        foreignKeyMessages.put("FK_COMPANY_ADDRESS", "Unable to delete address with company");
        foreignKeyMessages.put("FK_DIVISION_SUPPLIER", "Unable to delete supply company with divisions");
        foreignKeyMessages.put("FK_DRIVER_LOAD", "Unable to delete load attached to a driver");
        foreignKeyMessages.put("FK_DROPYARD_ADDRESS", "Unable to delete address with drop yard");
        foreignKeyMessages.put("FK_DROPYARD_COMPANY", "Unable to delete company with drop yards");
        foreignKeyMessages.put("FK_EMPLOYMENT_DRIVER", "Unable to delete employment with drivers");
        foreignKeyMessages.put("FK_EMPLOYMENT_HAULER", "Unable to delete employment with haulers");
        foreignKeyMessages.put("FK_HAULER_COMPANY", "Unable to delete company with hauler");
        foreignKeyMessages.put("FK_INT_APP_COMPANY", "Unable to delete company with integration application");
        foreignKeyMessages.put("FK_PLANTCONTACT_PLANT", "Unable to delete plant with plant contacts");
        foreignKeyMessages.put("FK_PLANTCONTACT_USER", "Unable to delete plant contact with plants");
        foreignKeyMessages.put("FK_PLANT_ADDRESS", "Unable to delete address with plant");
        foreignKeyMessages.put("FK_PLANT_COMPANY", "Unable to delete company with plants");
        foreignKeyMessages.put("FK_PRDUSER_PRODUCER", "Unable to delete producer with users");
        foreignKeyMessages.put("FK_PRDUSER_USER", "Unable to delete user with producers");
        foreignKeyMessages.put("FK_PRODUCER_ADDRESS", "Unable to delete address with producer");
        foreignKeyMessages.put("FK_PRODUCER_BTU", "Unable to delete BTU with producers");
        foreignKeyMessages.put("FK_PRODUCER_DIVISION", "Unable to delete division with producers");
        foreignKeyMessages.put("FK_PRODUCER_SUPPLIER", "Unable to delete supplier with producers");
        foreignKeyMessages.put("FK_SUPPLIER_COMPANY", "Unable to delete company with supplier");
        foreignKeyMessages.put("FK_TANKER_BARCODE", "Unable to delete barcode with tanker");
        foreignKeyMessages.put("FK_TANKER_COMPANY", "Unable to delete company with tankers");
        foreignKeyMessages.put("FK_TANK_BARCODE", "Unable to delete barcode with tank");
        foreignKeyMessages.put("FK_TANK_PRODUCER", "Unable to delete producer with tanks");
        foreignKeyMessages.put("FK_TRUCK_BARCODE", "Unable to delete barcode with truck");
        foreignKeyMessages.put("FK_TRUCK_COMPANY", "Unable to delete company with trucks");
        foreignKeyMessages.put("FK_YARD_FOREMAN_PLANT", "Unable to delete plant with yard foremen");
        foreignKeyMessages.put("FK_YARD_FOREMAN_USER", "Unable to delete yard foreman with plants");

        // UI Validation FKs
        foreignKeyMessages.put("FK_CONFIGFIELD_CONFIG", "Unable to delete active UI Configuration with field(s) already in use");
        foreignKeyMessages.put("FK_VALIDATIONPARM_TYPE", "Unable to delete validation type with validation parameters");
        foreignKeyMessages.put("FK_VALIDATION_CONFIGFIELD", "Unable to delete configuration field with validations");
        foreignKeyMessages.put("FK_VALIDATION_VALIDATIONTYPE", "Unable to delete validation type with validations");

        // Transaction data FKs
        foreignKeyMessages.put("FK_DELIVERY_PLANT", "Unable to delete plant with delivery");
        foreignKeyMessages.put("FK_DELIVERY_SCALETKIMAGE", "Unable to delete scale ticket image with delivery");
        foreignKeyMessages.put("FK_DELIVERY_SIGNATURE", "Unable to delete signature with delivery");
        foreignKeyMessages.put("FK_DELIVERY_TRANSPORTLEG", "Unable to delete transport leg with deliveries");
        foreignKeyMessages.put("FK_LOAD_GEOLOC", "Unable to delete geo location associated with a load");
        foreignKeyMessages.put("FK_LOADTANKER_LOAD", "Unable to delete load with load tankers");
        foreignKeyMessages.put("FK_LOADTANKER_TANKER", "Unable to delete tanker associated with a load");
        foreignKeyMessages.put("FK_LOADTRUCK_TRANSPORTLEG", "Unable to delete transport leg with load trucks");
        foreignKeyMessages.put("FK_LOADTRUCK_TRUCK", "Unable to delete truck associated with a transport leg");
        foreignKeyMessages.put("FK_LOADNOTE_LOAD", "Unable to delete load with load notes");
        foreignKeyMessages.put("FK_LOADNOTE_USER", "Unable to delete user with load notes");
        foreignKeyMessages.put("FK_MANIFEST_LOAD", "Unable to delete load with manifests");
        foreignKeyMessages.put("FK_MANIFEST_SUPPLIER", "Unable to delete supplier with manifests");
        foreignKeyMessages.put("FK_PICKUP_MANIFEST", "Unable to delete manifest with producer pickups");
        foreignKeyMessages.put("FK_PICKUP_PRODUCER", "Unable to delete producer with producer pickups");
        foreignKeyMessages.put("FK_PICKUP_SCALETKIMAGE", "Unable to delete scale ticket image with producer pickups");
        foreignKeyMessages.put("FK_PICKUP_TANK", "Unable to delete tank with producer pickups");
        foreignKeyMessages.put("FK_PICKUP_TANKER", "Unable to delete tanker leg with producer pickups");
        foreignKeyMessages.put("FK_PICKUP_TRANSPORTLEG", "Unable to delete transport leg with producer pickups");
        foreignKeyMessages.put("FK_REFUSAL_PLANT", "Unable to delete plant with refusals");
        foreignKeyMessages.put("FK_REFUSAL_TRANSPORTLEG", "Unable to delete transport leg with refusals");
        foreignKeyMessages.put("FK_SAMPLE_PICKUP", "Unable to delete producer pickup with samples");
        foreignKeyMessages.put("FK_SAMPLE_PURPOSE", "Unable to delete sample purpose with samples");
        foreignKeyMessages.put("FK_SEAL_LOADTANKER", "Unable to delete load tanker with seals");
        foreignKeyMessages.put("FK_SEAL_LOADTRUCK", "Unable to delete load truck with seals");
        foreignKeyMessages.put("FK_SEAL_SEALLOC", "Unable to delete seal location with seals");
        foreignKeyMessages.put("FK_TANKERDROP_TRANSPORTLEG", "Unable to delete transport leg with tanker drops");
        foreignKeyMessages.put("FK_TANKERPICKUP_TRANSPORTLEG", "Unable to delete transport leg with tanker pickups");
        foreignKeyMessages.put("FK_TRANSPORTLEG_DRIVER", "Unable to delete driver with transport legs");
        foreignKeyMessages.put("FK_TRANSPORTLEG_HAULER", "Unable to delete hauler with transport legs");
        foreignKeyMessages.put("FK_TRANSPORTLEG_LOAD", "Unable to delete load with transport legs");
        foreignKeyMessages.put("FK_TRANSPORTLEG_TRUCK", "Unable to delete truck with transport legs");

        // Check Constraints
        checkMessages.put("CHECK_HAS_TRUCK_OR_TANKER", "A Seal requires either a Tanker or a Truck");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @RequestMapping(produces=ApiVersion.V1_0_0_ERROR)
    public ResponseEntity<VndErrors> handleDataIntegrityViolationException(HttpServletRequest request,
                                                                           DataIntegrityViolationException e) {
        String message = e.getMostSpecificCause().getMessage();
        String logref = ThreadLocalRandom.current().nextInt() + "";
        log.error("logref:" + logref + " - Cannot save entity: " + message);
        logRequestBody(request);
        e.printStackTrace();
        if (message.contains("UQ_")) {
            String uniqueKey = message.substring(message.indexOf("UQ_"), message.indexOf(" ", message.indexOf("UQ_")) - 1);
            // H2 appends INDEX_# to the end of the name, Oracle
            if (uniqueKey.contains("_INDEX_")) {
                uniqueKey = uniqueKey.replaceAll("_INDEX_.*", "");
            }
            String friendlyMessage = uniqueKeyMessages.get(uniqueKey);
            if (friendlyMessage == null) {
                friendlyMessage = message;
            }
            return ResponseEntity.unprocessableEntity()
                    .body(new VndErrors(logref, friendlyMessage));
        }
        else if (message.contains("FK_")) {
            String foreignKey = message.substring(message.indexOf("FK_"), message.indexOf(" ", message.indexOf("FK_")) - 1);
            // Note: H2 appends ':' to the end of the name, Oracle has a ')'
            String friendlyMessage = foreignKeyMessages.get(foreignKey);
            if (friendlyMessage == null) {
                friendlyMessage = message;
            }
            return ResponseEntity.unprocessableEntity()
                    .body(new VndErrors(logref, friendlyMessage));
        } else if (message.contains("CHECK_")) {
            String check = message.substring(message.indexOf("CHECK_"), message.indexOf(" ", message.indexOf("CHECK_")) - 1);
            String friendlyMessage = foreignKeyMessages.get(check);
            if (friendlyMessage == null) {
                friendlyMessage = message;
            }
            return ResponseEntity.unprocessableEntity()
                    .body(new VndErrors(logref, friendlyMessage));
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new VndErrors(logref, message));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @RequestMapping(produces=ApiVersion.V1_0_0_ERROR)
    public ResponseEntity<VndErrors> handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException e) {
        String logref = ThreadLocalRandom.current().nextInt() + "";
        log.error("logref:" + logref + " - Entity failed validation");
        logRequestBody(request);
        e.printStackTrace();
        List<VndErrors.VndError> errors = new ArrayList<>();
        e.getConstraintViolations().forEach(err -> {
            String message;
            if (err.getConstraintDescriptor() != null && err.getConstraintDescriptor().getAnnotation().annotationType().equals(AssertTrue.class)) {
                message = err.getMessage();
            }
            else if (err.getPropertyPath() == null) {
                if (err.getMessage() == null) {
                    message = err.getMessageTemplate();
                }
                else {
                    message = err.getMessage();
                }
            }
            else {
                message = err.getPropertyPath() + ": " + err.getMessage() + "(" + err.getInvalidValue() + ")";
            }
            log.error("logref:" + logref + " - " + message);
            errors.add(new VndErrors.VndError(logref, message));
        });
        return ResponseEntity.unprocessableEntity()
                .body(new VndErrors(errors, null, null, null));
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    @RequestMapping(produces=ApiVersion.V1_0_0_ERROR)
    public ResponseEntity<VndErrors> handleOptimisticLockException(HttpServletRequest request, OptimisticLockingFailureException e) {
        String logref = ThreadLocalRandom.current().nextInt() + "";
        log.error("logref:" + logref + " - Entity failed optimistic lock");
        logRequestBody(request);
        e.printStackTrace();
        List<VndErrors.VndError> errors = new ArrayList<>();
        String message = e.getMessage();
        if (message == null) {
            message = "Optimistic Lock.  Try again with refreshed data.";
        }
        errors.add(new VndErrors.VndError(logref, message));

        return ResponseEntity.unprocessableEntity()
                .body(new VndErrors(errors, null, null, null));
    }

    @ExceptionHandler(StaleObjectStateException.class)
    @RequestMapping(produces=ApiVersion.V1_0_0_ERROR)
    public ResponseEntity<VndErrors> handleStaleObjectException(HttpServletRequest request, StaleObjectStateException e) {
        String logref = ThreadLocalRandom.current().nextInt() + "";
        log.error("logref:" + logref + " - Entity failed optimistic lock");
        logRequestBody(request);
        e.printStackTrace();
        List<VndErrors.VndError> errors = new ArrayList<>();
        String message = e.getMessage();
        if (message == null) {
            message = "Optimistic Lock.  Try again with refreshed data.";
        }
        errors.add(new VndErrors.VndError(logref, message));

        return ResponseEntity.unprocessableEntity()
                .body(new VndErrors(errors, null, null, null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @RequestMapping(produces=ApiVersion.V1_0_0_ERROR)
    public ResponseEntity<VndErrors> handleMethodArgumentNotValidException(HttpServletRequest request,
                                                                           MethodArgumentNotValidException e) {
        String logref = ThreadLocalRandom.current().nextInt() + "";
        log.error("logref:" + logref + " - Entity failed validation");
        logRequestBody(request);
        e.printStackTrace();
        List<VndErrors.VndError> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(err -> {
            String message="";
            if (err.getCode().equals("AssertTrue")) {
                message = err.getDefaultMessage();
            }else if (err instanceof FieldError) {
                message = err.getObjectName() + "." + ((FieldError)err).getField() + ": " + err.getDefaultMessage();
            }
            log.error("logref:" + logref + " - " + message);
            errors.add(new VndErrors.VndError(logref, message));
        });
        return ResponseEntity.unprocessableEntity()
                .body(new VndErrors(errors, null, null, null));
    }

    @ExceptionHandler(TransactionSystemException.class)
    @RequestMapping(produces=ApiVersion.V1_0_0_ERROR)
    public ResponseEntity<VndErrors> handleTransactionSystemException(HttpServletRequest request, TransactionSystemException e) {
        return delegateException(request, e.getMostSpecificCause());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @RequestMapping(produces=ApiVersion.V1_0_0_ERROR)
    public ResponseEntity<VndErrors> handleBadCredentialsException(HttpServletRequest request, TransactionSystemException e) {
        return warnOnException("Bad Login Attempt", e);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @RequestMapping(produces=ApiVersion.V1_0_0_ERROR)
    public ResponseEntity<VndErrors> handleAccessDeniedException(HttpServletRequest request, TransactionSystemException e) {
        return warnOnException("Access to Resource Denied", e);
    }

    @ExceptionHandler(ClientAbortException.class)
    @RequestMapping(produces=ApiVersion.V1_0_0_ERROR)
    public ResponseEntity<VndErrors> handleClientAbortException(HttpServletRequest request, TransactionSystemException e) {
        return warnOnException("Client aborted.", e);
    }

    private ResponseEntity<VndErrors> warnOnException(String logMessage, Throwable e) {
        String logref = ThreadLocalRandom.current().nextInt() + "";
        List<VndErrors.VndError> errors = new ArrayList<>();
        String message = e.getMessage();
        log.warn(logMessage);
        if (message == null) {
            message = logMessage;
        }
        errors.add(new VndErrors.VndError(logref, message));
        return ResponseEntity.badRequest().body(new VndErrors(errors, null, null, null));
    }


    @ExceptionHandler(Throwable.class)
    @RequestMapping(produces=ApiVersion.V1_0_0_ERROR)
    public ResponseEntity<VndErrors> handleException(HttpServletRequest request, Throwable e) {
        String message = e.getMessage();
        Link link = null;
        if (message == null) {
            message = "Server Error";
            link = Link.of(e.getClass().getName());
        }
        String body = "";
        try {
            body = request.getReader().lines().collect(Collectors.joining());
        } catch(Exception exception) {
            log.error("Couldn't parse body for error", exception);
        }
        String logref = ThreadLocalRandom.current().nextInt() + "";
        if (message.equals("Access is denied")){
            log.warn("logref: {} - Server Error: {}", logref, message);
            log.warn("logref: {} - URL: {}", logref, request.getRequestURI());
        }
        else{
            log.error("logref: {} - Server Error: {}", logref, message);
            log.error("logref: {} - URL: {}", logref, request.getRequestURI());
            log.error("logref: {} - Body: {}", logref, body);
            log.error("logref: " + logref + " - Stack Trace: ", e);
            e.printStackTrace();
        }
        VndErrors errors;
        if (link != null) {
            errors = new VndErrors(logref, message, link);
        } else {
            errors = new VndErrors(logref, message);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errors);
    }

    ResponseEntity<VndErrors> delegateException(HttpServletRequest request, Throwable t) {
        ResponseEntity<VndErrors> response = null;
        switch (t.getClass().getSimpleName()) {
            case "ConstraintViolationException":
                response = handleConstraintViolationException(request, (ConstraintViolationException)t);
                break;
            default:
                response = handleException(request, t);
                break;
        }
        return response;

        // Trying to be too fancy maybe...
//		return switchType(t,
//				caze(ConstraintViolationException.class, cve -> handleConstraintViolationException(request, cve)),
//				caze(Throwable.class, cve -> handleException(request, cve))
//		);
    }

    private void logRequestBody(HttpServletRequest request) {
        switch (request.getMethod()) {
            case "POST":
                // sets a boolean so the RequestBodyErrorFilter will log it
                request.setAttribute(LOG_REQUEST_BODY_ATTRIBUTE_NAME, Boolean.TRUE);
                break;
            case "PUT": break;
            case "GET":
                // sets a boolean so the RequestBodyErrorFilter will log it
                request.setAttribute(LOG_REQUEST_BODY_ATTRIBUTE_NAME, Boolean.FALSE);
                break;

            case "PATCH":
                // sets a boolean so the RequestBodyErrorFilter will log it
                request.setAttribute(LOG_REQUEST_BODY_ATTRIBUTE_NAME, Boolean.TRUE);
                break;

            default:
                break;
        }
    }

}
