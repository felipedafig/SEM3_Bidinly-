using MainServer.WebAPI.Services;
using Microsoft.Extensions.Configuration;

namespace TestProject.Services
{
    /// <summary>
    /// Base class for DataTierGrpcClient tests providing shared setup
    /// </summary>
    public abstract class DataTierGrpcClientTestBase
    {
        protected readonly IConfiguration Configuration;

        protected DataTierGrpcClientTestBase()
        {
            var configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.AddInMemoryCollection(new Dictionary<string, string?>
            {
                { "DataTier:Address", "http://localhost:9093" }
            });
            Configuration = configurationBuilder.Build();
        }

        protected DataTierGrpcClient CreateClient()
        {
            return new DataTierGrpcClient(Configuration);
        }
    }
}

