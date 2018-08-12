package com.sergtm.entities;

import com.sergtm.model.ServiceName;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDateTime;

@Entity
@Table(name = "SERVICE_STATUS")
public class ServiceStatus implements IEntity, Comparable<ServiceStatus> {
    @Id
    @SequenceGenerator(name = "SERVICE_STATUS_SEQ", sequenceName = "SERVICE_STATUS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SERVICE_STATUS_SEQ")
    @Column(name = "ID")
    @XmlTransient
    private Long id;

    @Column(name = "SERVICE_NAME")
    @Enumerated(EnumType.STRING)
    private ServiceName serviceName;

    @Column(name = "LAST_MODIFICATION_TIME")
    private LocalDateTime lastModificationTime;

    @Override
    public int compareTo(ServiceStatus other) {
        LocalDateTime otherLastModification = null;
        if (other != null) {
            otherLastModification = other.lastModificationTime;
        }
        if (this.lastModificationTime == otherLastModification) {
            return 0;
        } else if (otherLastModification == null) {
            return 1;
        } else if (this.lastModificationTime == null){
            return -1;
        }
        return this.lastModificationTime.compareTo(otherLastModification);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceName getServiceName() {
        return serviceName;
    }

    public void setServiceName(ServiceName serviceName) {
        this.serviceName = serviceName;
    }

    public LocalDateTime getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(LocalDateTime lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }
}
