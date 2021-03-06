package org.lunifera.dsl.entity.xtext.tests.model.testcarstore1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.lunifera.dsl.entity.xtext.tests.model.testcarstore1.Person;

@Entity
@Table(name = "EMPLOYEE")
@DiscriminatorValue(value = "EMPLOYEE")
@SuppressWarnings("all")
public class Employee extends Person {
  @Column(name = "EMPLOYEENUMBER")
  private long employeenumber;
  
  @Column(name = "EMPLOYEESINCE")
  @Temporal(value = TemporalType.TIME)
  private Date employeesince;
  
  @Column(name = "SALARY")
  private int salary;
  
  @Column(name = "JOBDESCRIPTION")
  private String jobdescription;
  
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "BOSS", nullable = false)
  private Employee boss;
  
  @JoinColumn(name = "SUBORDINATES")
  @OneToMany(mappedBy = "boss")
  private List<Employee> subordinates;
  
  /**
   * Checks whether the object is disposed.
   * @throws RuntimeException if the object is disposed.
   */
  private void checkDisposed() {
    if (isDisposed()) {
      throw new RuntimeException("Object already disposed: " + this);
    }
  }
  
  /**
   * Calling dispose will destroy that instance. The internal state will be 
   * set to 'disposed' and methods of that object must not be used anymore. 
   * Each call will result in runtime exceptions.<br/>
   * If this object keeps composition containments, these will be disposed too. 
   * So the whole composition containment tree will be disposed on calling this method.
   */
  public void dispose() {
    if (isDisposed()) {
      return;
    }
    super.dispose();
  }
  
  /**
   * Returns the employeenumber property or <code>null</code> if not present.
   */
  public long getEmployeenumber() {
    checkDisposed();
    return this.employeenumber;
  }
  
  /**
   * Sets the employeenumber property to this instance.
   */
  public void setEmployeenumber(final long employeenumber) {
    checkDisposed();
    this.employeenumber = employeenumber;
  }
  
  /**
   * Returns the employeesince property or <code>null</code> if not present.
   */
  public Date getEmployeesince() {
    checkDisposed();
    return this.employeesince;
  }
  
  /**
   * Sets the employeesince property to this instance.
   */
  public void setEmployeesince(final Date employeesince) {
    checkDisposed();
    this.employeesince = employeesince;
  }
  
  /**
   * Returns the salary property or <code>null</code> if not present.
   */
  public int getSalary() {
    checkDisposed();
    return this.salary;
  }
  
  /**
   * Sets the salary property to this instance.
   */
  public void setSalary(final int salary) {
    checkDisposed();
    this.salary = salary;
  }
  
  /**
   * Returns the jobdescription property or <code>null</code> if not present.
   */
  public String getJobdescription() {
    checkDisposed();
    return this.jobdescription;
  }
  
  /**
   * Sets the jobdescription property to this instance.
   */
  public void setJobdescription(final String jobdescription) {
    checkDisposed();
    this.jobdescription = jobdescription;
  }
  
  /**
   * Returns the <em>required</em> boss property.
   */
  public Employee getBoss() {
    checkDisposed();
    return this.boss;
  }
  
  /**
   * Sets the boss property to this instance.
   * Since the reference is a container reference, the opposite reference (Employee.subordinates)
   * of the boss will be handled automatically and no further coding is required to keep them in sync.
   * See {@link Employee#setSubordinates(Employee)}.
   */
  public void setBoss(final Employee boss) {
    checkDisposed();
    if (this.boss != null) {
      this.boss.internalRemoveFromSubordinates(this);
    }
    this.boss = boss;
    if (this.boss != null) {
      this.boss.internalAddToSubordinates(this);
    }
    
  }
  
  /**
   * Returns an unmodifiable list of subordinates.
   */
  public List<Employee> getSubordinates() {
    checkDisposed();
    return Collections.unmodifiableList(internalGetSubordinates());
  }
  
  /**
   * Returns the list of <code>Employee</code>s thereby lazy initializing it.
   */
  private List<Employee> internalGetSubordinates() {
    if (this.subordinates == null) {
      this.subordinates = new ArrayList<Employee>();
    }
    return this.subordinates;
  }
  
  /**
   * Adds the given employee to this object. <p>
   * Since the reference is a composition reference, the opposite reference (Employee.boss)
   * of the employee will be handled automatically and no further coding is required to keep them in sync. 
   * See {@link Employee#setBoss(Employee)}.
   * 
   */
  public void addToSubordinates(final Employee employee) {
    checkDisposed();
    employee.setBoss(this);
  }
  
  /**
   * Removes the given employee from this object. <p>
   * 
   */
  public void removeFromSubordinates(final Employee employee) {
    checkDisposed();
    employee.setBoss(null);
  }
  
  void internalAddToSubordinates(final Employee employee) {
    internalGetSubordinates().add(employee);
  }
  
  void internalRemoveFromSubordinates(final Employee employee) {
    internalGetSubordinates().remove(employee);
  }
}
