package via.pro3.datatierserver.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "\"User\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id\"")
    private Integer id;

    @Column(name = "\"Username\"", length = 50, nullable = false, unique = true)
    @NotNull
    @Size(max = 50)
    private String username;

    @Column(name = "\"Password\"", length = 100, nullable = false)
    @NotNull
    @Size(max = 100)
    private String password;

    @Column(name = "\"RoleId\"", nullable = false)
    @NotNull
    private Integer roleId;

    @Column(name = "\"IsActive\"", nullable = false)
    @NotNull
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"RoleId\"", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_role"))
    private Role role;

    // Constructors
    public User() {
    }

    public User(String username, String password, Integer roleId) {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.isActive = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}