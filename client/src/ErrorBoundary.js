import React, { Component } from 'react';
import Error from './components/Error/Error';

class ErrorBoundary extends Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false, error: null };
  }

  static getDerivedStateFromError(error) {
    return { hasError: true, error };
  }

  componentDidCatch(error, info) {
    // You can log or handle the error here
    console.log(info);
  }

  render() {
    if (this.state.hasError) {
      // Render your error component or a fallback UI
      return <Error error={this.state.error} />;
    }
    return this.props.children;
  }
}

export default ErrorBoundary;
