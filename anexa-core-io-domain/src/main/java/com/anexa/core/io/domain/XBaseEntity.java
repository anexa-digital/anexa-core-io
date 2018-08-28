package com.anexa.core.io.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.anexa.core.domain.IdentifiedDomainObject;
import com.anexa.core.domain.ObjectAuditableByTime;
import com.anexa.core.domain.VersionableObject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate(true)
@EntityListeners(AuditingEntityListener.class)
public abstract class XBaseEntity<ID>
		implements IdentifiedDomainObject<ID>, VersionableObject, ObjectAuditableByTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.PRIVATE)
	private ID id;

	@Version
	private int version;

	@CreatedDate
	@DateTimeFormat(style = "M-")
	@Column(name = "fechaCreacion", updatable = false)
	private LocalDateTime fechaCreacion;

	@LastModifiedDate
	@DateTimeFormat(style = "M-")
	@Column(name = "fechaModificacion")
	private LocalDateTime fechaModificacion;

	/**
	 * This `hashCode` implementation is specific for JPA entities and uses a fixed
	 * `int` value to be able to identify the entity in collections after a new id
	 * is assigned to the entity, following the article in
	 * https://vladmihalcea.com/2016/06/06/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (getId() == null)
			return false;

		@SuppressWarnings("unchecked")
		XBaseEntity<ID> other = (XBaseEntity<ID>) obj;

		return Objects.equals(getId(), other.getId());
	}
}
