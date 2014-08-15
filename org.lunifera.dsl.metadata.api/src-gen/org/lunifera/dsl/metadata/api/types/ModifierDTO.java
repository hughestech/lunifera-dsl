package org.lunifera.dsl.metadata.api.types;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

@SuppressWarnings("all")
public class ModifierDTO implements Serializable {
  private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
  
  private boolean disposed;
  
  private org.lunifera.dsl.metadata.api.types.OperationDTO operation;
  
  private boolean finalFlag;
  
  private boolean staticFlag;
  
  private org.lunifera.dsl.metadata.api.types.Visibility visibility;
  
  /**
   * Returns true, if the object is disposed. 
   * Disposed means, that it is prepared for garbage collection and may not be used anymore. 
   * Accessing objects that are already disposed will cause runtime exceptions.
   */
  public boolean isDisposed() {
    return this.disposed;
  }
  
  /**
   * @see PropertyChangeSupport#addPropertyChangeListener(PropertyChangeListener)
   */
  public void addPropertyChangeListener(final PropertyChangeListener listener) {
    propertyChangeSupport.addPropertyChangeListener(listener);
  }
  
  /**
   * @see PropertyChangeSupport#addPropertyChangeListener(String, PropertyChangeListener)
   */
  public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
  }
  
  /**
   * @see PropertyChangeSupport#removePropertyChangeListener(PropertyChangeListener)
   */
  public void removePropertyChangeListener(final PropertyChangeListener listener) {
    propertyChangeSupport.removePropertyChangeListener(listener);
  }
  
  /**
   * @see PropertyChangeSupport#removePropertyChangeListener(String, PropertyChangeListener)
   */
  public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
    propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
  }
  
  /**
   * @see PropertyChangeSupport#firePropertyChange(String, Object, Object)
   */
  public void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) {
    propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
  }
  
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
    disposed = true;
  }
  
  /**
   * Returns the operation property or <code>null</code> if not present.
   */
  public org.lunifera.dsl.metadata.api.types.OperationDTO getOperation() {
    checkDisposed();
    return this.operation;
  }
  
  /**
   * Sets the <code>operation</code> property to this instance.
   * Since the reference has an opposite reference, the opposite <code>OperationDTO#
   * modifier</code> of the <code>operation</code> will be handled automatically and no 
   * further coding is required to keep them in sync.<p>
   * See {@link OperationDTO#setModifier(OperationDTO)
   * 
   * @param operation - the property
   * @throws RuntimeException if instance is <code>disposed</code>
   * 
   */
  public void setOperation(final org.lunifera.dsl.metadata.api.types.OperationDTO operation) {
    checkDisposed();
    if (this.operation != null) {
      this.operation.internalSetModifier(null);
    }
    internalSetOperation(operation);
    if (this.operation != null) {
      this.operation.internalSetModifier(this);
    }
    
  }
  
  /**
   * For internal use only!
   */
  void internalSetOperation(final org.lunifera.dsl.metadata.api.types.OperationDTO operation) {
    firePropertyChange("operation", this.operation, this.operation = operation);
  }
  
  /**
   * Returns the finalFlag property or <code>null</code> if not present.
   */
  public boolean isFinalFlag() {
    checkDisposed();
    return this.finalFlag;
  }
  
  /**
   * Sets the <code>finalFlag</code> property to this instance.
   * 
   * @param finalFlag - the property
   * @throws RuntimeException if instance is <code>disposed</code>
   * 
   */
  public void setFinalFlag(final boolean finalFlag) {
    firePropertyChange("finalFlag", this.finalFlag, this.finalFlag = finalFlag );
  }
  
  /**
   * Returns the staticFlag property or <code>null</code> if not present.
   */
  public boolean isStaticFlag() {
    checkDisposed();
    return this.staticFlag;
  }
  
  /**
   * Sets the <code>staticFlag</code> property to this instance.
   * 
   * @param staticFlag - the property
   * @throws RuntimeException if instance is <code>disposed</code>
   * 
   */
  public void setStaticFlag(final boolean staticFlag) {
    firePropertyChange("staticFlag", this.staticFlag, this.staticFlag = staticFlag );
  }
  
  /**
   * Returns the visibility property or <code>null</code> if not present.
   */
  public org.lunifera.dsl.metadata.api.types.Visibility getVisibility() {
    checkDisposed();
    return this.visibility;
  }
  
  /**
   * Sets the <code>visibility</code> property to this instance.
   * 
   * @param visibility - the property
   * @throws RuntimeException if instance is <code>disposed</code>
   * 
   */
  public void setVisibility(final org.lunifera.dsl.metadata.api.types.Visibility visibility) {
    firePropertyChange("visibility", this.visibility, this.visibility = visibility );
  }
}
