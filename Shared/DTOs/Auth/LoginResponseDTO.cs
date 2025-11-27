using Shared.DTOs.Users;

namespace Shared.DTOs.Auth;
public class LoginResponse
{
    public string Token { get; set; } = "";
    public UserDto User { get; set; } = null!;
}