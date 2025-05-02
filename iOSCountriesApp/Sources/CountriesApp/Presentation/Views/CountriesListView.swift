import SwiftUI

struct CountriesListView: View {
    @StateObject private var viewModel: CountriesListViewModel
    
    init(viewModel: CountriesListViewModel) {
        _viewModel = StateObject(wrappedValue: viewModel)
    }
    
    var body: some View {
        NavigationView {
            ZStack {
                List(viewModel.state.countries) { country in
                    NavigationLink(destination: CountryDetailsView(
                        viewModel: CountryDetailsViewModel(
                            countryCode: country.code,
                            getCountryDetailUseCase: GetCountryDetailUseCase(apollo: ApolloClient(url: URL(string: "YOUR_GRAPHQL_ENDPOINT")!))
                        )
                    )) {
                        CountryRow(country: country)
                    }
                }
                
                if viewModel.state.isLoading {
                    ProgressView()
                }
            }
            .navigationTitle("Countries")
        }
    }
}

struct CountryRow: View {
    let country: SimpleCountry
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(country.name)
                .font(.headline)
            Text(country.code)
                .font(.subheadline)
                .foregroundColor(.gray)
        }
        .padding(.vertical, 8)
    }
} 